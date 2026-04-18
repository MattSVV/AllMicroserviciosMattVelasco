namespace appVelascoDTOs.DTOs
{
    public class BaseResponse<TResult>
    {
        public bool success { get; set; }

        public string? ErrorMessage { get; set; }

        public TResult? Result { get; set; }


    }

}
